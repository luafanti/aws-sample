var timer = new easytimer.Timer();
var levelTime = parseInt(document.getElementById('levelTime').value);
var fullTime = parseInt(document.getElementById('levelTime').value);
var level = parseInt(1);

var blind1Small = parseInt(0);
var blind2Small = parseInt(0);
var blind3Small = parseInt(0);
var blind4Small = parseInt(0);
var blind5Small = parseInt(0);
var blind6Small = parseInt(0);
var blind7Small = parseInt(0);
var blind8Small = parseInt(0);
var blind9Small = parseInt(0);
var blind10Small = parseInt(0);
var blind11Small = parseInt(0);
var blind12Small = parseInt(0);
var blind13Small = parseInt(0);
var blind14Small = parseInt(0);
var blind15Small = parseInt(0);
var blind16Small = parseInt(0);

var blind1Big = parseInt(0);
var blind2Big = parseInt(0);
var blind3Big = parseInt(0);
var blind4Big = parseInt(0);
var blind5Big = parseInt(0);
var blind6Big = parseInt(0);
var blind7Big = parseInt(0);
var blind8Big = parseInt(0);
var blind9Big = parseInt(0);
var blind10Big = parseInt(0);
var blind11Big = parseInt(0);
var blind12Big = parseInt(0);
var blind13Big = parseInt(0);
var blind14Big = parseInt(0);
var blind15Big = parseInt(0);
var blind16Big = parseInt(0);



$('#chronoExample .startButton').click(function () {
    timer.start({precision: 'seconds'});
    document.getElementById('level').innerHTML = level.toString();
    document.getElementById("saveButton").disabled = true;

    $(function(){
        $("input.blind").attr("disabled", true);
    });
});


$('#chronoExample .pauseButton').click(function () {
    timer.pause();

});
$('#chronoExample .stopButton').click(function () {

    timer.stop();
    level = parseInt(1);
    levelTime = parseInt(document.getElementById('levelTime').value);
    fullTime = parseInt(document.getElementById('levelTime').value);
    document.getElementById("saveButton").disabled = false;


    document.getElementById("startButton").disabled = true;
    document.getElementById("stopButton").disabled = true;
    document.getElementById("pauseButton").disabled = true;

    $(function(){
        $("input.blind").attr("disabled", false);
    });
});


$('#chronoExample .saveButton').click(function () {

    levelTime = parseInt(document.getElementById('levelTime').value) * 60;
    fullTime = parseInt(document.getElementById('levelTime').value) * 60;

    document.getElementById("startButton").disabled = false;
    document.getElementById("stopButton").disabled = false;
    document.getElementById("pauseButton").disabled = false;

    blind1Small = parseInt(document.getElementById('lvl1small').value);
    blind2Small = parseInt(document.getElementById('lvl2small').value);
    blind3Small = parseInt(document.getElementById('lvl3small').value);
    blind4Small = parseInt(document.getElementById('lvl4small').value);
    blind5Small = parseInt(document.getElementById('lvl5small').value);
    blind6Small = parseInt(document.getElementById('lvl6small').value);
    blind7Small = parseInt(document.getElementById('lvl7small').value);
    blind8Small = parseInt(document.getElementById('lvl8small').value);
    blind9Small = parseInt(document.getElementById('lvl9small').value);
    blind10Small = parseInt(document.getElementById('lvl10small').value);
    blind11Small = parseInt(document.getElementById('lvl11small').value);
    blind12Small = parseInt(document.getElementById('lvl12small').value);
    blind13Small = parseInt(document.getElementById('lvl13small').value);
    blind14Small = parseInt(document.getElementById('lvl14small').value);
    blind15Small = parseInt(document.getElementById('lvl15small').value);
    blind16Small = parseInt(document.getElementById('lvl16small').value);


    blind1Big = parseInt(document.getElementById('lvl1big').value);
    blind2Big = parseInt(document.getElementById('lvl2big').value);
    blind3Big = parseInt(document.getElementById('lvl3big').value);
    blind4Big = parseInt(document.getElementById('lvl4big').value);
    blind5Big = parseInt(document.getElementById('lvl5big').value);
    blind6Big = parseInt(document.getElementById('lvl6big').value);
    blind7Big = parseInt(document.getElementById('lvl7big').value);
    blind8Big = parseInt(document.getElementById('lvl8big').value);
    blind9Big = parseInt(document.getElementById('lvl9big').value);
    blind10Big = parseInt(document.getElementById('lvl10big').value);
    blind11Big = parseInt(document.getElementById('lvl11big').value);
    blind12Big = parseInt(document.getElementById('lvl12big').value);
    blind13Big = parseInt(document.getElementById('lvl13big').value);
    blind14Big = parseInt(document.getElementById('lvl14big').value);
    blind15Big = parseInt(document.getElementById('lvl15big').value);
    blind16Big = parseInt(document.getElementById('lvl16big').value);



    document.getElementById('blindSmall').innerHTML = blind1Small.toString();
    document.getElementById('blindBig').innerHTML = blind1Big.toString();
});


timer.addEventListener('secondsUpdated', function (e) {
    $('#chronoExample .values').html(timer.getTimeValues().toString());
    $('#chronoExample .progress_bar').html($('#chronoExample .progress_bar').html() + '.');

    if (timer.getTotalTimeValues().seconds > fullTime) {
        alert("Blinds up !");
        level = level + 1;
        fullTime = fullTime + levelTime;
        document.getElementById('level').innerHTML = level.toString();

        if(level == 1){
            document.getElementById('blindSmall').innerHTML = blind1Small.toString();
            document.getElementById('blindBig').innerHTML = blind1Big.toString();
        }

        if(level == 2){
            document.getElementById('blindSmall').innerHTML = blind2Small.toString();
            document.getElementById('blindBig').innerHTML = blind2Big.toString();
        }

        if(level == 3){
            document.getElementById('blindSmall').innerHTML = blind3Small.toString();
            document.getElementById('blindBig').innerHTML = blind3Big.toString();
        }

        if(level == 4){
            document.getElementById('blindSmall').innerHTML = blind4Small.toString();
            document.getElementById('blindBig').innerHTML = blind4Big.toString();
        }

        if(level == 5){
            document.getElementById('blindSmall').innerHTML = blind5Small.toString();
            document.getElementById('blindBig').innerHTML = blind5Big.toString();
        }

        if(level == 6){
            document.getElementById('blindSmall').innerHTML = blind6Small.toString();
            document.getElementById('blindBig').innerHTML = blind6Big.toString();
        }

        if(level == 7){
            document.getElementById('blindSmall').innerHTML = blind7Small.toString();
            document.getElementById('blindBig').innerHTML = blind7Big.toString();
        }
        if(level == 8){
            document.getElementById('blindSmall').innerHTML = blind8Small.toString();
            document.getElementById('blindBig').innerHTML = blind8Big.toString();
        }

        if(level == 9){
            document.getElementById('blindSmall').innerHTML = blind9Small.toString();
            document.getElementById('blindBig').innerHTML = blind9Big.toString();
        }

        if(level == 10){
            document.getElementById('blindSmall').innerHTML = blind10Small.toString();
            document.getElementById('blindBig').innerHTML = blind10Big.toString();
        }
        if(level == 11){
            document.getElementById('blindSmall').innerHTML = blind11Small.toString();
            document.getElementById('blindBig').innerHTML = blind11Big.toString();
        }
        if(level == 12){
            document.getElementById('blindSmall').innerHTML = blind12Small.toString();
            document.getElementById('blindBig').innerHTML = blind12Big.toString();
        }
        if(level == 13){
            document.getElementById('blindSmall').innerHTML = blind13Small.toString();
            document.getElementById('blindBig').innerHTML = blind13Big.toString();
        }
        if(level == 14){
            document.getElementById('blindSmall').innerHTML = blind14Small.toString();
            document.getElementById('blindBig').innerHTML = blind14Big.toString();
        }
        if(level == 15){
            document.getElementById('blindSmall').innerHTML = blind15Small.toString();
            document.getElementById('blindBig').innerHTML = blind15Big.toString();
        }
        if(level == 16){
            document.getElementById('blindSmall').innerHTML = blind16Small.toString();
            document.getElementById('blindBig').innerHTML = blind16Big.toString();
        }

    }

    console.log("LEVEL " + level);
    console.log("LEVEL TIME" + fullTime);
});


timer.addEventListener('started', function (e) {
    $('#chronoExample .values').html(timer.getTimeValues().toString());
});
timer.addEventListener('reset', function (e) {
    $('#chronoExample .values').html(timer.getTimeValues().toString());
});

timer.addEventListener('targetAchieved', function (e) {
    $('#startValuesAndTargetExample .progress_bar').html('COMPLETE!!');
});


function popupOpenClose(popup) {

    /* Add div inside popup for layout if one doesn't exist */
    if ($(".wrapper").length == 0){
        $(popup).wrapInner("<div class='wrapper'></div>");
    }

    /* Open popup */
    $(popup).show();

    /* Close popup if user clicks on background */
    $(popup).click(function(e) {
        if ( e.target == this ) {
            if ($(popup).is(':visible')) {
                $(popup).hide();
            }
        }
    });

    /* Close popup and remove errors if user clicks on cancel or close buttons */
    $(popup).find("button[name=close]").on("click", function() {
        if ($(".formElementError").is(':visible')) {
            $(".formElementError").remove();
        }
        $(popup).hide();
    });
}

$(document).ready(function () {
    $("[data-js=open]").on("click", function() {
        popupOpenClose($(".popup"));
    });
});

