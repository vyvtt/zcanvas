/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

function loadFile(event) {
    var preview = document.getElementById('preview');
    preview.style.display = 'inline-block';
    preview.src = URL.createObjectURL(event.target.files[0]);
    var palette = document.getElementById('palette');
    palette.style.display = 'none';
}