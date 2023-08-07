package com.lolhistory.datamodel

import com.google.gson.annotations.SerializedName

data class MatchHistory(
    @SerializedName("info")
    var info: Info
) {
    data class Info(
        @SerializedName("gameCreation")
        var gameCreation: Long,

        @SerializedName("gameDuration")
        var gameDuration: Long,

        @SerializedName("gameVersion")
        var gameVersion: String,

        @SerializedName("queueId")
        var queueId: Int,

        @SerializedName("participants")
        var participants: Participants
    ) {
        data class Participants(
            @SerializedName("assists")
            var assists: Int,

            @SerializedName("champLevel")
            var champLevel: Int,

            @SerializedName("championId")
            var championId: Int,

            @SerializedName("championName")
            var champName: String,

            @SerializedName("deaths")
            var deaths: Int,

            @SerializedName("item0")
            var item0: Int,

            @SerializedName("item1")
            var item1: Int,

            @SerializedName("item2")
            var item2: Int,

            @SerializedName("item3")
            var item3: Int,

            @SerializedName("item4")
            var item4: Int,

            @SerializedName("item5")
            var item5: Int,

            @SerializedName("item6")
            var item6: Int,

            @SerializedName("kills")
            var kills: Int,

            @SerializedName("neutralMinionsKilled")
            var neutralMinionsKilled: Int,

            @SerializedName("puuid")
            var puuid: String,

            @SerializedName("summoner1Id")
            var summoner1Id: Int,

            @SerializedName("summoner2Id")
            var summoner2Id: Int,

            @SerializedName("summonerLevel")
            var summonerLevel: Int,

            @SerializedName("summonerName")
            var summonerName: String,

            @SerializedName("totalMinionsKilled")
            var totalMinionsKilled: Int,

            @SerializedName("win")
            var win: Boolean,

            @SerializedName("perks")
            var perks: Perks
        ) {
            data class Perks(
                @SerializedName("statPerks")
                var statPerks: StatPerks,

                @SerializedName("styles")
                var style: List<Style>
            ) {
                data class StatPerks(
                    @SerializedName("defense")
                    var defense: Int,

                    @SerializedName("flex")
                    var flex: Int,

                    @SerializedName("offense")
                    var offense: Int
                )
                data class Style(
                    @SerializedName("description")
                    var description: String,

                    @SerializedName("style")
                    var style: Int,

                    @SerializedName("selections")
                    var selections: List<Selection>
                ) {
                    data class Selection(
                        @SerializedName("perk")
                        var perk: Int
                    )
                }
            }
        }
    }
}
