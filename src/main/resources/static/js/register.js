const regexZipCode =  /((0[1-9])|([1-8][0-9])|(9[0-8])|(2A)|(2B))[0-9]{3}/;
const inputZipCode = document.querySelector('#zipcode');
const spanZipCode  = document.getElementById('errorZipCode');
const inputPass  = document.getElementById('pass');
const inputConfPass  = document.getElementById('confpass');


spanZipCode.className= 'd-none';

inputZipCode.addEventListener('input', (event) =>{
    let valueZipCode = document.getElementById('zipcode').value;
    let found = valueZipCode.match(regexZipCode);

    if (!found){
        spanZipCode.className= 'd-block';
    }
    else{
        spanZipCode.className= 'd-none';
    }
});

inputZipCode.addEventListener('input', (event) =>{
    let valueZipCode = document.getElementById('zipcode').value;
    let found = valueZipCode.match(regexZipCode);

    if (!found){
        spanZipCode.className= 'd-block';
    }
    else{
        spanZipCode.className= 'd-none';
    }
});

inputConfPass.addEventListener('input', (event) =>{
    let valueZipCode = document.getElementById('zipcode').value;
    let found = valueZipCode.match(regexZipCode);

    if (!found){
        spanZipCode.className= 'd-block';
    }
    else{
        spanZipCode.className= 'd-none';
    }
});



