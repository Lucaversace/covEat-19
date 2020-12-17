const regexZipCode =  /((0[1-9])|([1-8][0-9])|(9[0-8])|(2A)|(2B))[0-9]{3}/;
const regexPass =  /(?=.{8,}$)(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9]).*/;
const inputZipCode = document.querySelector('#zipcode');
const spanZipCode  = document.getElementById('errorZipCode');
const inputPass  = document.querySelector('#pass');
const spanPass  = document.getElementById('errorPassword');

spanZipCode.className= 'd-none';
spanPass.className= 'd-none';

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

inputPass.addEventListener('input', (event) =>{
    let valuePass = document.getElementById('pass').value;
    let found = valuePass.match(regexPass);

    if (!found){
        spanPass.className= 'd-block';
    }
    else{
        spanPass.className= 'd-none';
    }
});



