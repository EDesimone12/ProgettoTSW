$(document).ready(function () {
    $(".pictureButton").click(function changeImage(){
        $("#mainPictureMedia").attr("src",$(this).children().attr("src"));
    });
});
