function updateOutput() {
    $("#resultSegment").innerHTML = "";

    const identityNumber = $("#identityNumber").val();
    const loanAmount = $("#loanAmount").val();
    const loanPeriod = $("#loanPeriod").val();

    // const identityNumber = "49002010976"; // 100
    // const loanAmount = "3100";
    // const loanPeriod = "12";

    if (identityNumber == null || identityNumber === "" ) {
        $("#resultSegment").text("Identity number can't be null");
    } else if (loanAmount == null || loanAmount === "") {
        $("#resultSegment").text("Loan amount can't be null");
    } else if (loanPeriod == null || loanPeriod === "") {
        $("#resultSegment").text("Loan period has to be selected");
    } else {
        $.get("/result?id=" + identityNumber + "&amount=" + loanAmount + "&period=" + loanPeriod).done(function (resultFragment) {
            console.log(resultFragment);
            $("#resultSegment").html(resultFragment);
        });
    }

    // $.post("/decision/test_ajax_frag").done(function (fragment) {
    //     console.log(fragment);
    //     $("#output").replaceWith(fragment);
    //
    // });

    // $.get("/decision/test_ajax_frag3/1/2/3").done(function (fragment) {
    //     console.log(fragment);
    //     $("#output").replaceWith(fragment);
    // });

    // $.ajax({
    //     type: "POST",
    //     contentType : 'application/json; charset=utf-8',
    //     url: "/decision/test_ajax_frag",
    //     data: JSON.stringify(userInput),
    //     dataType: 'json',
    //     cache: false,
    //     timeout: 600000,
    //     success: function (fragment) {
    //         $("#output").replaceWith(fragment);
    //     },
    //     error: function (e) {
    //
    //         // var json = "Ajax Hata"
    //         //     + e.responseText;
    //         // $('#feedback').html(json);
    //         console.log("hataa")
    //         console.log(e)
    //     }
    // });


    // $.ajax({
    //     type: "POST",
    //     contentType : 'application/json; charset=utf-8',
    //     url: "/decision/test_ajax_frag2",
    //     // data: JSON.stringify(userInput),
    //     // dataType: 'json',
    //     cache: false,
    //     timeout: 600000,
    //     success: function (fragment) {
    //         $("#output").replaceWith(fragment);
    //     },
    //     error: function (e) {
    //
    //         // var json = "Ajax Hata"
    //         //     + e.responseText;
    //         // $('#feedback').html(json);
    //         console.log("hataa")
    //         console.log(e)
    //     }
    // });
}
