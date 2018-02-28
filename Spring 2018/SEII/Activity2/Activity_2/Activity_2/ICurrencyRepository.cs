using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Activity_2
{
    public interface ICurrencyRepository
    {
        Currency getCurrency(CurrencyType type);
    }
}
