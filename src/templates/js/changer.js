// Selecting necessary elements from the DOM
const body = document.querySelector('body'),
      sidebar = body.querySelector('.sidebar'),
      toggle = body.querySelector('.toggle'),
      searchBtn = body.querySelector('.search-box'),
      modeSwitch = body.querySelector('.toggle-switch'),
      modeText = body.querySelector('.mode-text');

// Event listener for toggling the sidebar
toggle.addEventListener("click", () => {
    sidebar.classList.toggle("close"); // Toggling the 'close' class on the sidebar
});

// Event listener for opening the sidebar when search button is clicked
searchBtn.addEventListener("click", () => {
    sidebar.classList.remove("close"); // Removing the 'close' class from the sidebar
});

// Event listener for toggling dark mode
modeSwitch.addEventListener("click", () => {
    body.classList.toggle("dark"); // Toggling the 'dark' class on the body

    // Changing the mode text based on the body class
    if (body.classList.contains("dark")){
        modeText.innerText = "Light Mode"; // Changing text to 'Light Mode'
    }else{
        modeText.innerText = "Dark Mode"; // Changing text to 'Dark Mode'
    }
});