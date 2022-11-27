package com.inbank.creditdecisionengine.controller;

import com.inbank.creditdecisionengine.config.CreditDecisionConfig;
import com.inbank.creditdecisionengine.dto.UserCreditResultDto;
import com.inbank.creditdecisionengine.dto.UserInputDto;
import com.inbank.creditdecisionengine.exception.PersonNotFoundException;
import com.inbank.creditdecisionengine.service.CreditDecisionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@Slf4j
public class DecisionViewController {

    private final CreditDecisionService creditDecisionService;
    private final CreditDecisionConfig creditDecisionConfig;

    public DecisionViewController(CreditDecisionService creditDecisionService, CreditDecisionConfig creditDecisionConfig) {
        this.creditDecisionService = creditDecisionService;
        this.creditDecisionConfig = creditDecisionConfig;
    }

    @GetMapping("/index")
    public String showCreateUserForm(Model model) {
        log.info("ICERDEMA");
        model.addAttribute("creditLimitations", creditDecisionConfig);
        return "index";
    }

    @GetMapping(path = "/result")
    public String sendHtmlFragment5(@RequestParam("id") String id, @RequestParam("amount") Double amount, @RequestParam("period") Integer period, Model model) throws PersonNotFoundException {
        UserInputDto userInput = UserInputDto.builder().build();
        userInput.setIdentityNumber(id);
        userInput.setRequestedLoanAmount(amount);
        userInput.setRequestedLoanPeriod(period);
        UserCreditResultDto userCreditResult = creditDecisionService.getCreditResult(userInput);
        model.addAttribute("result", userCreditResult);
        return "result_fragment :: result_frag";
    }
}
