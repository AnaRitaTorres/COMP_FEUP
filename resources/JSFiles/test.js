var bull = 6*7;
var goat = 4/2;
var lion = 8-2;
var tuna = 125+3;
var bear = "hello" + " world";
var lynx = "my number is " + 4;
var puma = (3 + 3)*4;
var frog = [1,2,3,4];
var fish = "babe";
var b=new user(1,2,3);

function User(name,age){
    //var n=name; falta ver isto ainda não reconhece os argumentos
    //var a=age;
     var line="ola";
     var num=1;

    if(bull<goat){
        var ola="ola"+"ola";
    }

    while(bull<goat){
        bull=1; //temos de verificar se a variável foi declarada antes
    }

    while(bull <goat){ //é preciso tratar depois dos espaços para ficar o código alinhado no ficheiro java
        if(bull<goat){
            ola="ola";
        }
    }

    return line;
}

var k=0;

function main(){
    switch(puma){
        case 1:
            x=2;
            break;
        default:
            break;
    }

    for(var i=1;i < 4;i++){
        puma=2;
        i++;
    }

    do{
        i++;
    }while(i > 10);

    console.log("1");
    console.log(bull);
    console.log(bull>goat);
}