import java.net.URI

/**
 * Created by i352072(erica.cao@sap.com) on 04/01/2021
 */
class JsonMain {

    val URI_GOAL_AUTO_POPULATION_REQUEST =
        "/proxy/v1/bizx/odatav4/talent/tgm/GoalDetail%s.svc/Goal/GoalDetail%s.svc.onChange"

    fun getURL(): String {
        val goalTemplateId = 63
        val uriPath = String.format(URI_GOAL_AUTO_POPULATION_REQUEST,
            goalTemplateId, goalTemplateId)
        val uri = URI("https", null, "www.test.com", 443, uriPath,
            "\$expand=metricLookupTable", null)
        return uri.toString()
    }

}

fun main() {
    println("Starting...")
    val url = JsonMain().getURL()
    println(url)

}