package com.kidoo.customer.bean;

import java.io.Serializable;
import java.util.List;

/**
 * User: ShaudXiao
 * Date: 2017-12-15
 * Time: 14:46
 * Company: zx
 * Description:
 * FIXME
 */


public class CompetionDetailResult implements Serializable {

    private List<MedalBean> medalList;
    private List<CompetionAlbumBean> albumList;
    private MatchBean match;
    private List<CompetionEnrollbean> enrollList;
    private List<CompetionEpisodeBean> episodeList;
    private List<CompetionNodeBean> nodeList;

    public List<MedalBean> getMedalList() {
        return medalList;
    }

    public void setMedalList(List<MedalBean> medalList) {
        this.medalList = medalList;
    }

    public List<CompetionAlbumBean> getAlbumList() {
        return albumList;
    }

    public void setAlbumList(List<CompetionAlbumBean> albumList) {
        this.albumList = albumList;
    }

    public MatchBean getMatch() {
        return match;
    }

    public void setMatch(MatchBean match) {
        this.match = match;
    }

    public List<CompetionEnrollbean> getEnrollList() {
        return enrollList;
    }

    public void setEnrollList(List<CompetionEnrollbean> enrollList) {
        this.enrollList = enrollList;
    }

    public List<CompetionEpisodeBean> getEpisodeList() {
        return episodeList;
    }

    public void setEpisodeList(List<CompetionEpisodeBean> episodeList) {
        this.episodeList = episodeList;
    }

    public List<CompetionNodeBean> getNodeList() {
        return nodeList;
    }

    public void setNodeList(List<CompetionNodeBean> nodeList) {
        this.nodeList = nodeList;
    }

}
