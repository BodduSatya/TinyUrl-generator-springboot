import { useState } from "react"
import { ToastContainer, toast } from 'react-toastify';

const InputShortener = ({ setInputValue }) => {
  const [value, setValue] = useState("");

  const handleClick = () => {

      if( !value || value.length ===0  ) {
          toast('Please paste URL to shorten', {
              position: "top-center",
              autoClose: 5000,
              hideProgressBar: false,
              closeOnClick: true,
              pauseOnHover: true,
              draggable: true,
              progress: undefined,
              theme: "light",
          });
          return;
      }

    setInputValue(value);
    setValue("");
  }

  return (
    <div className="inputContainer">
      <h1>URL <span>Shortener</span></h1>
      <div>
        <input
          type="text"
          placeholder="Paste an URL to shorten it"
          value={value}
          onChange={e => setValue(e.target.value)}  
        />
        <button onClick={handleClick}>shorten</button>
      </div>
    </div>
  )
}

export default InputShortener