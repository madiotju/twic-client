var listeVilles = alterVariable;
var selectBox1 = document.getElementById('ville1selection');
var selectBox2 = document.getElementById('ville2selection');


for(var i = 0, l = listeVilles.length; i < l; i++){
    var ville = listeVilles[i];
    selectBox1.options.add( new Option(ville) );
    selectBox2.options.add(new Option(ville));
}