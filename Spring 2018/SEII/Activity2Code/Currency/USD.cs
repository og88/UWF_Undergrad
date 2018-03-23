using System;
using System.Collections.Generic;
using System.Text;

namespace Activity2
{
    class USD : Currency
    {
        double finalAmount = 0.0;

        public override double ConvertCurrency(double startingAmount, CurrencyType convertedCurrencyType)
        {
            if (convertedCurrencyType == CurrencyType.USD)
            {
                return finalAmount = startingAmount;
            }
            else if (convertedCurrencyType == CurrencyType.Pounds)
            {
                return finalAmount = 0.65 * startingAmount;
            }
            else if (convertedCurrencyType == CurrencyType.USD)
            {
                return finalAmount = 0.88 * startingAmount;
            }
            return 0;
        }
    }
}
