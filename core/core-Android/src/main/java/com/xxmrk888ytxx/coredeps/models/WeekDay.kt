package com.xxmrk888ytxx.coredeps.models

import android.content.Context
import com.xxmrk888ytxx.coredeps.R
import com.xxmrk888ytxx.coredeps.models.WeekDay.Companion.toStringLiteral
import kotlinx.collections.immutable.persistentSetOf

sealed class WeekDay(val number:Int) {
    object Monday : WeekDay(0)
    object Tuesday : WeekDay(1)
    object Wednesday : WeekDay(2)
    object Thursday : WeekDay(3)
    object Friday : WeekDay(4)
    object Saturday : WeekDay(5)
    object Sunday : WeekDay(6)

    companion object {

        fun fromInt(number: Int) : WeekDay {
            return when(number) {
                0 -> Monday

                1 -> Tuesday

                2 -> Wednesday

                3 -> Thursday

                4 -> Friday

                5 -> Saturday

                6 -> Sunday

                else -> throw IllegalArgumentException("Week day with id $number not exist")
            }
        }

        val weekDaySet = persistentSetOf<WeekDay>(
            WeekDay.Monday,
            WeekDay.Tuesday,
            WeekDay.Wednesday,
            WeekDay.Thursday,
            WeekDay.Friday,
            WeekDay.Saturday,
            WeekDay.Sunday
        )

        fun WeekDay.toStringLiteral(context: Context) : String {

            context.apply {
                return when(this@toStringLiteral) {
                    is Monday -> getString(R.string.Monday_literal)

                    is Tuesday -> getString(R.string.Tuesday_literal)

                    is Wednesday -> getString(R.string.Wednesday_literal)

                    is Thursday -> getString(R.string.Thursday_literal)

                    is Friday -> getString(R.string.Friday_literal)

                    is Saturday -> getString(R.string.Saturday_literal)

                    is Sunday -> getString(R.string.Sunday_literal)
                }
            }
        }

    }
}

