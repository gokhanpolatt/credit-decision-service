const updateOutput = () => {
    let resultSegment = $("#resultSegment");
    resultSegment.innerHTML = "";

    const identityNumber = $("#identityNumber").val();
    const loanAmount = $("#loanAmount").val();
    const loanPeriod = $("#loanPeriod").val();

    if (identityNumber == null || identityNumber === "") {
        resultSegment.text("Identity number can't be null");
    } else if (loanAmount == null || loanAmount === "") {
        resultSegment.text("Loan amount can't be null");
    } else if (loanPeriod == null || loanPeriod === "") {
        resultSegment.text("Loan period has to be selected");
    } else {
        $.get("/result?id=" + identityNumber + "&amount=" + loanAmount + "&period=" + loanPeriod)
            .done(function (resultFragment) {
                console.log(resultFragment);
                resultSegment.html(resultFragment);
            });
    }
}
