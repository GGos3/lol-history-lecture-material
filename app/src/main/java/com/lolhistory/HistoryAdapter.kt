package com.lolhistory

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.lolhistory.databinding.ItemMatchHistoryBinding
import com.lolhistory.datamodel.MatchHistory
import com.lolhistory.parser.QueueParser
import com.lolhistory.parser.RuneParser
import com.lolhistory.parser.SpellParser
import com.lolhistory.retrofit.BaseUrl
import java.util.Locale

class HistoryAdapter(
    private var matchHistories: ArrayList<MatchHistory>,
    private val puuid: String,
):RecyclerView.Adapter<HistoryAdapter.ViewHolder>() {

    // 컬러를 가져올 때 사용
    private lateinit var context: Context


    // 값을 지정할 때 사용
    inner class ViewHolder(
        private val binding: ItemMatchHistoryBinding
    ):RecyclerView.ViewHolder(binding.root) {
        fun bind(matchHistory: MatchHistory) {
            val playerIndex = getPlayerIndex(matchHistory)
            if (matchHistory.info.participants[playerIndex].win) {
                binding.layoutResult.setBackgroundColor(context.getColor(R.color.colorWin))
                binding.tvResult.setText(R.string.win)
            } else {
                binding.layoutResult.setBackgroundColor(context.getColor(R.color.colorDefeat))
                binding.tvResult.setText(R.string.defeat)
            }
            binding.tvDurationTime.text = getDurationTime(matchHistory.info.gameDuration)
            binding.tvGameType.text = getQueueType(matchHistory.info.queueId)

            Glide.with(context)
                .load(getChampionPortraitUrl(matchHistory.info.participants[playerIndex]))
                .into(binding.ivChampionPortrait)

            binding.tvKda.text = getKDA(matchHistory.info.participants[playerIndex])

            Glide.with(context)
                .load(getSpellImageUrl(matchHistory.info.participants[playerIndex], 1))
                .into(binding.ivSummonerSpell1)
            Glide.with(context)
                .load(getSpellImageUrl(matchHistory.info.participants[playerIndex], 2))
                .into(binding.ivSummonerSpell2)

            Glide.with(context)
                .load(getRuneImageUrl(matchHistory.info.participants[playerIndex], 1))
                .into(binding.ivKeyStoneRune)
            Glide.with(context)
                .load(getRuneImageUrl(matchHistory.info.participants[playerIndex], 2))
                .into(binding.ivSecondaryRune)

            val itemArray = arrayOf(
                binding.ivItem0,
                binding.ivItem1,
                binding.ivItem2,
                binding.ivItem3,
                binding.ivItem4,
                binding.ivItem5,
                binding.ivItem6,
            )
            for (i in itemArray.indices) {
                val itemUrl = getItemImageUrl(matchHistory.info.participants[playerIndex], i)
                if (itemUrl.isNotEmpty())
                    Glide.with(context).load(itemUrl).into(itemArray[i])
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        context = parent.context

        matchHistories.sortByDescending { it.info.gameCreation }
        val binding = ItemMatchHistoryBinding.inflate(
            LayoutInflater.from(context),
            parent,
            false
        )

        return ViewHolder(binding)
    }

    // 데이터 전달
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val matchHistory = matchHistories[position]
        holder.bind(matchHistory)
    }

    override fun getItemCount(): Int {
        return matchHistories.size
    }

    private fun getPlayerIndex(matchHistory: MatchHistory): Int {
        var index = 0

        for (i in matchHistory.info.participants.indices) {
            if (puuid == matchHistory.info.participants[i].puuid) {
                index = i
                break
            }
        }

        return index
    }

    private fun getDurationTime(secondTime: Long): String {
        val min = secondTime / 60
        val second = secondTime % 60
        return String.format(Locale.getDefault(), "%02d", min) + ":" + String.format(Locale.getDefault(), "%02d", second)
    }

    private fun getQueueType(queueId: Int): String {
        val parser = QueueParser(context)
        return parser.getQueueType(queueId)
    }

    private fun getChampionPortraitUrl(participants: MatchHistory.Info.Participants): String {
        val championName = participants.championName
        return BaseUrl.RIOT_DATA_DRAGON_GET_CHAMPION_PORTRAIT + "$championName.png"
    }

    private fun getKDA(participants: MatchHistory.Info.Participants): String {
        return "${participants.kills} / ${participants.deaths} / ${participants.assists}"
    }

    private fun getSpellImageUrl(
        participants: MatchHistory.Info.Participants, spellIdx: Int
    ): String {
        val spellId = when (spellIdx) {
            1 -> participants.summoner1Id
            2 -> participants.summoner2Id
            else -> 0
        }
        val spellName = SpellParser(context).getSpellName(spellId)
        return BaseUrl.RIOT_DATA_DRAGON_GET_SPELL_IMAGE + "$spellName.png"
    }

    private fun getRuneImageUrl(
        participants: MatchHistory.Info.Participants,
        runeIdx: Int
    ): String {
        var runeId = 0
        participants.perks.style.forEach {
            when (runeIdx) {
                1 -> if (it.description == "primaryStyle") runeId = it.selections[0].perk
                2 -> if (it.description == "subStyle") runeId = it.style
            }
        }
        val runeIcon = RuneParser(context).getRuneIcon(runeId)
        return BaseUrl.RIOT_DATA_DRAGON_GET_RUNE_IMAGE + runeIcon
    }

    private fun getItemImageUrl(
        participants: MatchHistory.Info.Participants,
        itemIdx: Int
    ): String {
        val itemId = when (itemIdx) {
            0 -> participants.item0
            1 -> participants.item1
            2 -> participants.item2
            3 -> participants.item3
            4 -> participants.item4
            5 -> participants.item5
            6 -> participants.item6
            else -> 0
        }
        return if (itemId == 0) ""
        else BaseUrl.RIOT_DATA_DRAGON_GET_ITEM_IMAGE + "$itemId.png"
    }
}