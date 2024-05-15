// Open dialog function
function openDialog(){
    // Take dialog by id
    const dialog = document.getElementById("word-dialog");
    dialog.showModal();

    // Add "add" function to our buttons when the dialog is open
    document.getElementById("submit").addEventListener("click", function() {
        // Take information from inputs
        const wordInput = document.getElementById("word");
        const translatedInput = document.getElementById("translated");

        // Check if inputs are empty and send information
        if (wordInput.value.trim() !== "" && translatedInput.value.trim() !== "") {
            // New data pattern
            const data = {
                "word": wordInput.value,
                "translated": translatedInput.value
            }

            // Parse data to json
            saveInfo(data);

            // Clear my inputs
            wordInput.value = '';
            translatedInput.value = '';
        }else {
            // Alert message if someone of the field is not fill
            alert("Please fill in all fields!");
        }
    });
}

// Close dialog function
function dialogClose(){
    // Take dialog by id
    const dialog = document.getElementById("word-dialog");
    dialog.close();
}

// Method for sending data to the server
function saveInfo(forData){
    // Url where will be send this information
    fetch("/word/Save", {
        // Header information
        method: "POST",
        headers: {
            "Content-Type": "application/json"
        },
        // Parse data to json format
        body: JSON.stringify(forData)
    })
    // Response from server if information is successfully send!
    .then(response => {
        // If information is not saved
        if (!response.ok){
            throw new Error("Network response was not ok");
            alert("Info was not saved!");
        }

        // Info is send successfully
        alert("Info was saved successfully!");
    })
    .catch(error => console.error("Error sending form data:", error));
}