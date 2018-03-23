using System;
using System.Collections.Generic;
using System.Text;

namespace Activity2
{
    class Euros : Currency
    {
        double finalAmount = 0.0;

        public override double ConvertCurrency(double startingAmount, CurrencyType convertedCurrencyType)
        {
            if (convertedCurrencyType == CurrencyType.USD)
            {
                return finalAmount = 1.14 * startingAmount;
            }
            else if (convertedCurrencyType == CurrencyType.Pounds)
            {
                return finalAmount = 0.74 * startingAmount;
            }
            else if (convertedCurrencyType == CurrencyType.USD)
            {
                return finalAmount = startingAmount;
            }
            return 0;
        }
    }
}
