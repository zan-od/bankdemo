package com.tpe.bankdemo.validator;

import com.tpe.bankdemo.model.BankTransaction;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class BankTransactionValidator implements Validator {

    @Override
    public boolean supports(Class<?> classToValidate) {
        return BankTransaction.class.equals(classToValidate);
    }

    @Override
    public void validate(Object object, Errors errors) {
        BankTransaction transaction = (BankTransaction) object;

        //ValidationUtils.rejectIfEmptyOrWhitespace(errors, "amount", "field.Required");

        if ((transaction.getSender() == null) & (transaction.getRecipient() == null)) {
            errors.rejectValue("sender", "transaction.EmptySenderAndRecipient");
        }

        if ((transaction.getSender() == transaction.getRecipient()) && (transaction.getSender() != null)) {
            errors.rejectValue("sender", "transaction.SenderAndRecipientAreEqual");
        }
    }
}
