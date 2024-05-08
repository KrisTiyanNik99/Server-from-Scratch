function saveInfo(){
    var cword = document.getElementById("word").value;
    var ctranslated = document.getElementById("translated").value;

    if ((firstName === null || lastName === null)
            || (firstName === "" || lastName === "")){
        alert("Please fill in all fields")
        return;
    }

    var formData = {
        word: cword,
        translated: ctranslated,
    };

/*            fetch("/save/word", {
                method: "POST",
                headers: {
                    "Content-Type": "application/json"
                },
                body: JSON.stringify(formData)
            })
            .then(response => {
                if (!response.ok) {
                    throw new Error("Network response was not ok");
                    alert("Info was not saved!")
                }
                console.log("Form data sent successfully!");
                alert("Info was saved successfully!")
            })
            .catch(error => console.error("Error sending form data:", error));

            document.getElementById("first_name").value = "";
            document.getElementById("last_name").value = "";
        } */