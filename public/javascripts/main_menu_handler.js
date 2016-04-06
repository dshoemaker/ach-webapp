/**
 * Created by Dan on 3/18/2016.
 */

$(document).ready(function(){
    // Toggle file drop visibility
    $('#drop_zone').hide();
    $('#import-btn').click(function() {
        $('#drop_zone').toggle();
        $('#file_info').empty();
    });

    // Initialize bootstrap tooltips for Action buttons
    $('.btn-action').tooltip();

    // Retrieve selected case to view data
    $('.clickable-row').click(function() {
        $('.case-view-container').empty();
        var id = $(this).data("caseNum");
        viewCase(jsCaseRoutes.controllers.CaseController.findCase(id).url);
    });
});


// File Drag & Drop
var dropZone = document.getElementById('drop_zone');
dropZone.addEventListener('dragover', handleDragOver);
dropZone.addEventListener('drop', handleDrop);
dropZone.addEventListener("dragenter", handleDragEnter);
dropZone.addEventListener("dragleave", handleDragLeave);


function handleDrop(evt) {
    evt.preventDefault();
    evt.stopPropagation();
    this.className = 'drop_zone';

    var fileList = evt.dataTransfer.files;
    var message = "<ul>";
    [].forEach.call(fileList, function(file) {
        message += "<li>" + file.name + " - " + file.type + "</li>";
    });
    message += "</ul>";

    document.getElementById("file_info").innerHTML = message;
    setStatus("Drop file here");
    return false;
}

function handleDragOver(evt) {
    evt.stopPropagation();
    evt.preventDefault();
    this.className = 'drop_zone dragover';
    return false;
}

function handleDragLeave(evt) {
    setStatus("Drop file here");
    this.className = 'drop_zone';
    return false;
}

function handleDragEnter(evt) {
    evt.stopPropagation();
    evt.preventDefault();
    setStatus("Copy file");
    return false;
}

function setStatus(text) {
    document.getElementById("drop_zone").innerHTML = text;
}
