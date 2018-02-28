using System;
using Microsoft.VisualStudio.TestTools.UnitTesting;
using Activity_2;
using Moq;

namespace CurrencyTest
{
    [TestClass]
    public class UnitTest2
    {
        [TestMethod]
        public void TestMethod1()
        {
            //Arrange
            var mockRepository = new Mock<ICurrencyRepository>();
            var currency = new Mock<Currency>(CurrencyType.Pounds);
            currency.Setup(t => t.ConvertCurrency(100, CurrencyType.USD)).Returns(100*1.54); ;
            mockRepository.Setup(t => t.getCurrency(CurrencyType.Pounds)).Returns(currency.Object);
            var sut = new CurrencyService(mockRepository.Object);



            //Act
            double convert = sut.convertCurrency(100, CurrencyType.Pounds, CurrencyType.USD);

            //Assert
            Assert.AreEqual(154, convert);
        }
    }
}
