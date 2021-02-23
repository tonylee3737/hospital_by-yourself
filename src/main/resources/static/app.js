const buttons = document.querySelectorAll("button");//버튼 가져오기
const input = document.querySelector(".show"); //class show를 가져온다.

let result = "";
let number = "";

function init() {
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
    input.value = input.value + number;
    result += number;
}
function operator(oper) {
    result = result.slice(0, -1);
    input.value = result;
}
function clear() {
    input.value = "";
    result = "";
}

init();