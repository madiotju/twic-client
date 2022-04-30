document.getElementById("page1").hidden = false;
for (let i=2; i<nbPages+1; i++) {
    document.getElementById("page"+i).setAttribute("hidden","");
}

function attributeCode(pageNb,villeNb) {
    let code = document.getElementById("codeCommune"+pageNb+','+villeNb).getAttribute("value");
    document.getElementById("code"+pageNb+','+villeNb).setAttribute("value",code);
}

function nextPage(pageNb) {
    pageNb ++;
    console.log(pageNb);
    if (pageNb < nbPages){
        document.getElementById("page"+pageNb).setAttribute("hidden","");
        let nextPg = pageNb +1;
        document.getElementById("page"+nextPg).hidden = false;
    }
}

function prevPage(pageNb) {
    pageNb ++;
    if (pageNb !== 1){
        document.getElementById("page"+pageNb).setAttribute("hidden","");
        let prevPage = pageNb -1;
        document.getElementById("page"+prevPage).hidden = false;
    }
}