import MentoringCardsOrderStrategy.extractTopTenCardsByRule
import MentoringCardsOrderStrategy.generateCards

/**
 * Created by i352072(erica.cao@sap.com) on 05/10/2023
 */
object MentoringCardsOrderStrategy {
    
    /**
     * The mentoring card consists of two types of cards:
     *  * recommend mentoring mentor card
     *  * recommend mentoring program card
     * The extraction condition for these cards are as follows:
     *  1. Extract a maximum of 10 recommend mentoring cards.
     *  2. Extract a maximum of 7 mentoring mentor cards.
     *  3. Extract a maximum of 3 mentoring program cards.
     */
    fun extractTopTenCardsByRule(items: MutableList<MentoringCard>) {
        // If the total items is less than or equal to 10, simply return the items.
        // This ensures that there will be a maximum of 10 cards to be displayed.
        if (items.size <= 10) {
            return
        }
        
        val mentorCards = items.filter { it is MentorCard }
        val programCards = items.filter { it is ProgramCard }
        
        items.clear()
        
        // The mentor cards have a higher priority than the program cards, and should be displayed
        // before them. Therefore, the first step is to ensure that an adequate number of mentor cards
        // are displayed, with a maximum of 7 out of a total of 10 cards. Any remaining slots will be
        // filled with program cards.
        if (mentorCards.size > 7) {
            // mentor > 7, program >= 3 => mentor = 7, program = 3
            // mentor > 7, program < 3  => mentor = (10 - program.size), program = program.size
            items.addAll(mentorCards.take(maxOf(7, 10 - programCards.size)))
            items.addAll(programCards.take(minOf(3, programCards.size)))
        } else {
            // mentor <= 7 => mentor = mentor.size, program = 10 - mentor.size
            items.addAll(mentorCards)
            items.addAll(programCards.take(10 - mentorCards.size))
        }
    }
    
    // Class<out MentoringCard> represents a class that is a subtype of `MentoringCard`,
    // this allows you to pass the class reference of either `ProgramCard` or `MentorCard`
    fun generateCards(cardType: Class<out MentoringCard>, n: Int): List<MentoringCard> {
        val list = mutableListOf<MentoringCard>()
        for (i in 1..n) {
            // `MentorCard::class` returns a `KClass<MentorCard>`, so here need to use the
            // `.java` property to obtain the `Class<MentorCard>` reference required for comparison.
            if (cardType == MentorCard::class.java) {
                list.add(MentorCard("mentor card: #$i"))
            } else {
                list.add(ProgramCard("program card: #$i"))
            }
        }
        return list
    }

}

data class ProgramCard(val name: String): MentoringCard
data class MentorCard(val name: String): MentoringCard
interface MentoringCard

fun <T>formatterPrintList(list: List<T>) {
    list.forEachIndexed { index, item ->
        println("${index + 1}:\t$item")
    }
}

fun main() {
    val mentorCardList = generateCards(MentorCard::class.java, 3)
    val programCardList = generateCards(ProgramCard::class.java, 18)
    // shuffled() returns a new list with elements in a random order.
    val mergedCardList = (mentorCardList + programCardList).shuffled().toMutableList()
//    val mergedCardList = (mentorCardList + programCardList).toMutableList()
    println("generate list:")
    formatterPrintList(mergedCardList)
    
    println("\nAfter extracting...")
    extractTopTenCardsByRule(mergedCardList)
    formatterPrintList(mergedCardList)
}