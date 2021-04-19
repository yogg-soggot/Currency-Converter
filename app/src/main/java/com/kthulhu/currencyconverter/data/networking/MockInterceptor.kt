package com.kthulhu.currencyconverter.data.networking

import okhttp3.*
import javax.inject.Inject

class MockInterceptor @Inject constructor() : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        return Response.Builder()
            .body(ResponseBody.create(MediaType.parse("application/json"), fakeJson))
            .code(200)
            .message("Mocked response")
            .protocol(Protocol.HTTP_1_0)
            .request(chain.request())
            .build()
    }

    companion object {
        const val fakeJson = "{\"result\":\"success\",\"provider\":\"https://www.exchangerate-api.com\",\"documentation\":\"https://www.exchangerate-api.com/docs/free\",\"terms_of_use\":\"https://www.exchangerate-api.com/terms\",\"time_last_update_unix\":1618704151,\"time_last_update_utc\":\"Sun, 18 Apr 2021 00:02:31 +0000\",\"time_next_update_unix\":1618866482,\"time_next_update_utc\":\"Mon, 19 Apr 2021 00:26:21 +0000\",\"time_eol_unix\":0,\"base_code\":\"USD\",\"rates\":{\"USD\":1,\"AED\":3.67,\"AFN\":77.73,\"ALL\":103.06,\"AMD\":522.26,\"ANG\":1.79,\"AOA\":650.25,\"ARS\":92.75,\"AUD\":1.29,\"AWG\":1.79,\"AZN\":1.7,\"BAM\":1.63,\"BBD\":2,\"BDT\":84.78,\"BGN\":1.63,\"BHD\":0.376,\"BIF\":1948.09,\"BMD\":1,\"BND\":1.33,\"BOB\":6.87,\"BRL\":5.62,\"BSD\":1,\"BTN\":74.67,\"BWP\":10.85,\"BYN\":2.6,\"BZD\":2,\"CAD\":1.25,\"CDF\":1989.33,\"CHF\":0.92,\"CLP\":701.92,\"CNY\":6.53,\"COP\":3644.59,\"CRC\":611.21,\"CUC\":1,\"CUP\":25.75,\"CVE\":92.02,\"CZK\":21.63,\"DJF\":177.72,\"DKK\":6.23,\"DOP\":57.09,\"DZD\":132.64,\"EGP\":15.67,\"ERN\":15,\"ETB\":41.83,\"EUR\":0.835,\"FJD\":2.03,\"FKP\":0.724,\"FOK\":6.23,\"GBP\":0.724,\"GEL\":3.43,\"GGP\":0.724,\"GHS\":5.77,\"GIP\":0.724,\"GMD\":52.02,\"GNF\":10061.81,\"GTQ\":7.7,\"GYD\":213.21,\"HKD\":7.77,\"HNL\":24.04,\"HRK\":6.29,\"HTG\":82.65,\"HUF\":301.7,\"IDR\":14509.78,\"ILS\":3.28,\"IMP\":0.724,\"INR\":74.67,\"IQD\":1464.38,\"IRR\":41952.76,\"ISK\":126.65,\"JMD\":150.34,\"JOD\":0.709,\"JPY\":108.82,\"KES\":107.21,\"KGS\":84.92,\"KHR\":4063.22,\"KID\":1.29,\"KMF\":410.55,\"KRW\":1115.61,\"KWD\":0.3,\"KYD\":0.833,\"KZT\":430.63,\"LAK\":9418.28,\"LBP\":1507.5,\"LKR\":197.2,\"LRD\":172.82,\"LSL\":14.27,\"LYD\":4.53,\"MAD\":8.93,\"MDL\":17.91,\"MGA\":3771.83,\"MKD\":51.41,\"MMK\":1410.27,\"MNT\":2859.75,\"MOP\":8.01,\"MRU\":35.63,\"MUR\":40.41,\"MVR\":15.38,\"MWK\":784.07,\"MXN\":19.95,\"MYR\":4.13,\"MZN\":55.41,\"NAD\":14.27,\"NGN\":399.49,\"NIO\":35.08,\"NOK\":8.38,\"NPR\":119.47,\"NZD\":1.4,\"OMR\":0.384,\"PAB\":1,\"PEN\":3.63,\"PGK\":3.51,\"PHP\":48.42,\"PKR\":152.57,\"PLN\":3.79,\"PYG\":6336.1,\"QAR\":3.64,\"RON\":4.11,\"RSD\":98.28,\"RUB\":75.92,\"RWF\":999.5,\"SAR\":3.75,\"SBD\":7.96,\"SCR\":18.57,\"SDG\":382.35,\"SEK\":8.44,\"SGD\":1.33,\"SHP\":0.724,\"SLL\":10250.64,\"SOS\":580.64,\"SRD\":14.21,\"SSP\":177.73,\"STN\":20.45,\"SYP\":1249.43,\"SZL\":14.27,\"THB\":31.19,\"TJS\":11.31,\"TMT\":3.5,\"TND\":2.75,\"TOP\":2.26,\"TRY\":8.07,\"TTD\":6.79,\"TVD\":1.29,\"TWD\":28.31,\"TZS\":2317.03,\"UAH\":27.97,\"UGX\":3615.28,\"UYU\":44.14,\"UZS\":10448.15,\"VES\":2362710.36,\"VND\":23057.11,\"VUV\":108.28,\"WST\":2.51,\"XAF\":547.4,\"XCD\":2.7,\"XDR\":0.7,\"XOF\":547.4,\"XPF\":99.58,\"YER\":250.74,\"ZAR\":14.27,\"ZMW\":22.17}}"
    }
}