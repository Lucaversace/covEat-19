const regexZipCode =  /((0[1-9])|([1-8][0-9])|(9[0-8])|(2A)|(2B))[0-9]{3}/;
const inputZipCode = document.querySelector('#zipcode');
const spanZipCode  = document.getElementById('errorZipCode');



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

})



