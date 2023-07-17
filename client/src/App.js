import './App.css';
import {Button} from "@mui/material";
import {useState} from "react";
import {formatISO9075} from "date-fns";
import {saveAs} from 'file-saver';


function App() {
    const [data, setData] = useState(null)
    const [error, setError] = useState(null)
    const [loading, setLoading] = useState(true)

    const handleButtonClick = () => {
        fetch("http://localhost:8445/api/v1/report")
            .then(response => {
                if (response.ok) {
                    return response.blob()
                }
                throw response
            })
            .then(data => {
                saveAs(data, `report${formatISO9075(new Date(), {format: "basic"})}.xlsx`)
            })
            .catch(error => {
                console.error("Error fetching data: ", error)
                setError(error)
            })
            .finally(() => {
                setLoading(false)
            })
    }

    return (
        <div className="App">
            <Button onClick={handleButtonClick}>Получить отчет</Button>
        </div>
    );
}

export default App;
