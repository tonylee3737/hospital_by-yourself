let buttons;
let input;
let input2;
let result = "";
let number = "";

function init() {
    buttons = document.querySelectorAll("button");//버튼 가져오기
    input = document.querySelector(".show"); //class show를 가져온다.
    input2 = document.querySelector(".show2"); //class show를 가져온다.

    buttons.forEach((button) => {
        button.addEventListener("click", () => {
            switch (button.dataset.type) {
                case "operator": //- 를 눌렀을때,
                    const oper = button.innerText;
                    operator(oper);
                    break;
                case "ac": //클리어 함수
                    clear();
                    break;

                default: //단순 보여주기 번호일때
                    number = button.innerText;
                    displayNumber(number);
                    break;
            }
        });
    });
}

function displayNumber(number) {

    if(input.value.length > 3){
        // alert("4자리 이상은 입력하실 수 없습니다.");
    }else if(input.value.length < 4){
    input.value = input.value + number;
    input2.value = input2.value + number;
    result += number;
    }
}
function operator(oper) {
    result = input.value;
    result = input2.value;
    result = result.slice(0, -1);
    input.value = result;
    input2.value = result;
}
function clear() {
    input.value = "";
    result = "";
}


function init_m() {
    buttons = document.querySelectorAll("button");//버튼 가져오기
    input = document.querySelector(".show"); //class show를 가져온다.

    buttons.forEach((button) => {
        button.addEventListener("click", () => {
            switch (button.dataset.type) {
                case "operator": //- 를 눌렀을때,
                    const oper = button.innerText;
                    operator(oper);
                    break;
                case "ac": //클리어 함수
                    clear();
                    break;

                default: //단순 보여주기 번호일때
                    number = button.innerText;
                    displayNumber(number);
                    break;
            }
        });
    });
}

function displayNumber(number) {

    if(input.value.length > 3){
        // alert("4자리 이상은 입력하실 수 없습니다.");
    }else if(input.value.length < 4){
        input.value = input.value + number;
        result += number;
    }
}
function operator(oper) {
    result = input.value;
    result = result.slice(0, -1);
    input.value = result;
}
function clear() {
    input.value = "";
    result = "";
}