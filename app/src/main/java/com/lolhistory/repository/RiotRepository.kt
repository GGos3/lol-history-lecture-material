package com.lolhistory.repository

import com.lolhistory.datamodel.MatchHistory
import com.lolhistory.datamodel.SummonerIdInfo
import com.lolhistory.datamodel.SummonerRankInfo
import com.lolhistory.retrofit.APIClient
import com.lolhistory.retrofit.RiotAPI
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

object RiotRepository {
    private val riotAPI = APIClient.getRiotClient().create(RiotAPI::class.java)
    private val riotV5API = APIClient.getRiotV5Client().create(RiotAPI::class.java)

    fun getSummonerIdInfo(summonerName: String): Single<SummonerIdInfo> = riotAPI
        .getSummonerIdInfo(summonerName)
        // 스레드에서 이벤트를 대기하다 메인 스레드로 넘겨줌
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())

    fun getSummonerRankInfo(summonerId: String): Single<List<SummonerRankInfo>> = riotAPI
        .getSummonerRankInfo(summonerId)
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())

    fun getMatchHistoryList(
        puuid: String,
        start: Int,
        count: Int
    ): Single<List<String>> = riotV5API
        .getMatchHistoryList(puuid, start, count)
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())

    fun getMatchHistory(matchId: String): Single<MatchHistory> = riotV5API
        .getMatchHistory(matchId)
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
}