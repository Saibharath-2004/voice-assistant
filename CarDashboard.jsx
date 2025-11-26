import React, { useState, useEffect, useRef } from "react";

const API_URL = "http://localhost:8080/api/command";

export const CarDashboard=()=> {
  const [command, setCommand] = useState("");
  const [state, setState] = useState({
    acOn: false,
    temperature: 22,
    headlightsOn: false,
    leftWindowOpen: false,
    rightWindowOpen: false,
    musicOn: false,
  });
  const [message, setMessage] = useState("");
  const [log, setLog] = useState([]);
  const [listening, setListening] = useState(false);
  const recognitionRef = useRef(null);

  useEffect(() => {
    const SpeechRecognition = window.SpeechRecognition || window.webkitSpeechRecognition;
    if (!SpeechRecognition) {
      recognitionRef.current = null;
      return;
    }
    const rec = new SpeechRecognition();
    rec.lang = "en-US";
    rec.interimResults = false;
    rec.maxAlternatives = 1;

    rec.onresult = (event) => {
      const text = event.results[0][0].transcript;
      setCommand(text);
      sendCommand(text);
    };

    rec.onend = () => setListening(false);
    rec.onerror = () => setListening(false);

    recognitionRef.current = rec;
  }, []);
//functional component
  const startListening = () => {
    if (recognitionRef.current) {
      try {
        recognitionRef.current.start();
        setListening(true);
      } catch (e) {
        console.error("start error", e);
      }
    } else {
      alert("SpeechRecognition not supported in this browser. Use Chrome.");
    }
  };


  //functional component
  const stopListening = () => {
    if (recognitionRef.current) {
      recognitionRef.current.stop();
      setListening(false);
    }
  };
//func comp
  const sendCommand = async (text = command) => {
    if (!text || !text.trim()) return;
    try {
      const res = await fetch(API_URL, {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify({ text }),
      });
      const data = await res.json();
      setState(data.state);
      setMessage(data.message);
      setLog((prev) => [{ command: text, response: data.message }, ...prev]);
      setCommand("");
      speak(`Done. ${data.message}`);
    } catch (err) {
      console.error(err);
      setMessage("Backend error");
      speak("There was an error contacting backend.");
    }
  };


  //func comp
  const speak = (text) => {
    if (!("speechSynthesis" in window)) return;
    const ut = new SpeechSynthesisUtterance(text);
    window.speechSynthesis.cancel();
    window.speechSynthesis.speak(ut);
  };

  return (
    <div className="container">
      <h2>Car Voice Control — Simulator</h2>

      <div className="controls">
        <input
          type="text"
          value={command}
          onChange={(e) => setCommand(e.target.value)}
          onKeyDown={(e) => e.key === "Enter" && sendCommand()}
          placeholder='Try: "turn on ac", "increase temperature", "open left window"'
        />
        <button onClick={() => sendCommand()}>Send</button>
        <button onClick={startListening} disabled={listening}>
          {listening ? "Listening..." : "Start Voice"}
        </button>
        <button onClick={stopListening} disabled={!listening}>
          Stop
        </button>
      </div>

      <p className="message"><strong>System:</strong> {message}</p>

      <div className="status-grid">
        <div className="card">
          <h4>AC</h4>
          <p>Status: <span className={state.acOn ? "on" : "off"}>{state.acOn ? "ON" : "OFF"}</span></p>
          <p>Temperature: {state.temperature}°C</p>
        </div>

        <div className="card">
          <h4>Headlights</h4>
          <p className={state.headlightsOn ? "on" : "off"}>{state.headlightsOn ? "ON" : "OFF"}</p>
        </div>

        {/*<div className="card">
          <h4>Windows</h4>
          <p>Left: {state.leftWindowOpen ? "OPEN" : "CLOSED"}</p>
          <p>Right: {state.rightWindowOpen ? "OPEN" : "CLOSED"}</p>
        </div>*/}

        <div className="card">
          <h4>Music</h4>
          <p>{state.musicOn ? "PLAYING" : "OFF"}</p>
        </div>
      </div>

      <div className="log">
        <h3>Command Log</h3>
        {log.length === 0 && <p>No commands yet.</p>}
        <ul>
          {log.map((item, i) => (
            <li key={i}>
              <strong>You:</strong> {item.command} <br />
              <strong>Sys:</strong> {item.response}
            </li>
          ))}
        </ul>
      </div>
    </div>
  );
}
