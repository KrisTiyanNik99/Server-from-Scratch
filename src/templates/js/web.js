function loadInfo(){
            fetch("/stud/info")
            .then(response => {
                if (!response.ok) {
                    throw new Error("Network response was not ok");
                    alert("Something wrong with the data file!")
                }

                return response.json();
            })
            .then(data => {
                if (!data) {
                    throw new Error("No data received from server");
                }

                var html = "";
                data.forEach(student => {
                    html +="<tr>";
                    html += "<td>" + student.first_name + "</td>";
                    html += "<td>" + student.last_name + "</td>";
                    html += "<td>" + student.age + "</td>";
                    html += "</tr>";
                });

                document.getElementById("studTable").innerHTML = html
            })
            .catch(error => console.log("Error loading students info:", error));
        }

        function saveInfo(){
            var firstName = document.getElementById("first_name").value;
            var lastName = document.getElementById("last_name").value;
            var age = document.getElementById("age").value;

            if ((firstName === null || lastName === null || age === null)
                || (firstName === "" || lastName === "" || age === "")){
                alert("Please fill in all fields")
                return;
            }

            var formData = {
                first_name: firstName,
                last_name: lastName,
                age: age
            };

            fetch("/save/student", {
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
            document.getElementById("age").value = "";
        }