function miniReport(){
    const xhr = new XMLHttpRequest();
    const container = document.getElementById('home');

    xhr.onload = function(){
        if (this.status === 200){
            console.log(xhr);
            container.innerHTML = xhr.responseText;
            console.log(container);
        }else{
            console.warn("Something is wrong!")
        }
    }

    xhr.open('get', 'test.html');
    xhr.send();
}