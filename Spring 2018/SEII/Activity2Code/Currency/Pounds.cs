using System;
using System.Collections.Generic;
using System.Text;

namespace Activity2
{
    class Pounds : Currency
    {
        public override double ConvertCurrency(double startingAmount, CurrencyType convertedCurrencyType)
        {
            double finalAmount = 0.0;

            if (convertedCurrencyType == CurrencyType.USD)
            {
                return finalAmount = 1.54 * startingAmount;
            }
            else if (convertedCurrencyType == CurrencyType.Pounds)
            {
                return finalAmount = startingAmount;

            }
            else if (convertedCurrencyType == CurrencyType.USD)
            {
                return finalAmount = 1.35 * startingAmount;
            }
            return 0;
        }
    }
}
