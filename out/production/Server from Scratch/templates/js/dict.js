// Function to load information from the server
function loadInfo(){
    // Fetching data from the "/dict/info" endpoint
    fetch("/dict/info")
    .then(response => {
        // Checking if the response is successful
        if (!response.ok) {
            // Throwing an error if network response is not okay
            throw new Error("Network response was not ok");

            // Alerting the user if there's an issue with the data file
            alert("Something wrong with the data file!")
        }

        return response.json();
    })
    // Handling the retrieved data
    .then(data => {
        // Selecting the container where the data will be displayed
        const postContainer = document.querySelector('.card-container');

        // Method to create and append post elements to the container
        const postMethods = () => {
            // Mapping through the data to create individual card elements
            data.map((postData) => {
                // Creating a new div element for each card
                const postElement = document.createElement('div')

                // Adding a class to the card element
                postElement.classList.add('card');

                // Setting the HTML content of the card element
                postElement.innerHTML = `
                    <h3 class="card-heading">${postData.word}</h3>
                    <p class="card-body">${postData.translated}</p>
                `
                // Appending the card element to the container
                postContainer.appendChild(postElement)
            })
        }
        // Calling the method to create and append posts
        postMethods()
    })
    // Catching and logging any errors that occur
    .catch(error => console.log("Error loading students info:", error));
}

// Calling the loadInfo function to initiate the data loading process
loadInfo()