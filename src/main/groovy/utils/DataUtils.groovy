package utils

import org.apache.commons.lang.RandomStringUtils

class DataUtils {

    def static getRandomString(int length, String charset) {
        return RandomStringUtils.random(length, charset)
    }

    def static getStringOfSpecialCharacters() {
        return "._|.&lt;(+_+)&gt;.|_.  ._|.&lt;(+_+)&gt;.|_.\nthis text is entered ) for ( testing special characters\n]|I{•------» 7357!n9 «------•}I|["
    }
}
