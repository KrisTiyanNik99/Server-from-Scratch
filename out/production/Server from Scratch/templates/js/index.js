// Function to get AJAX element
function getAjaxElement(){
    // Get container element by ID
    const container = document.getElementById('home');

    // Create new XMLHttpRequest object
    const xhr = new XMLHttpRequest();
    // Initialize variable to store href value
    var hrefValue = "";

    // Document ready function
    $(document).ready(function() {
        // Event listener for click on anchor elements
        $("a").click(function(event){
            // Get data-filename attribute value from clicked anchor element
            hrefValue = $(this).data("filename");
            console.log(hrefValue);

            // Using fetch API to fetch data
            fetch("/" + hrefValue)
            .then(response => {
                // Check if response is ok
                if (!response.ok){
                    throw new Error('Network response was not ok');
                }
                // Return response text
                return response.text();
            })
            .then(data => {
                // Insert fetched data into container
                $(container).html(data);
            })
            .catch(error => {
                // Handle errors
                console.error('There was a problem with your fetch operation:', error);
            })
        })
    });
}

// Call our function
getAjaxElement()