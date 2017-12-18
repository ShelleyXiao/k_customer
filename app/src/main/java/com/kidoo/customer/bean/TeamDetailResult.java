package com.kidoo.customer.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by ShaudXiao on 2017/12/18.
 */

public class TeamDetailResult implements Serializable {

    private TeamBean team;
    private List<MedalBean> medalList;
    private List<TeamMemberBen> applyMemberList;
    private List<?> challengeList;

    public TeamBean getTeam() {
        return team;
    }

    public void setTeam(TeamBean team) {
        this.team = team;
    }

    public List<MedalBean> getMedalList() {
        return medalList;
    }

    public void setMedalList(List<MedalBean> medalList) {
        this.medalList = medalList;
    }

    public List<TeamMemberBen> getApplyMemberList() {
        return applyMemberList;
    }

    public void setApplyMemberList(List<TeamMemberBen> applyMemberList) {
        this.applyMemberList = applyMemberList;
    }

    public List<?> getChallengeList() {
        return challengeList;
    }

    public void setChallengeList(List<?> challengeList) {
        this.challengeList = challengeList;
    }

    @Override
    public String toString() {
        return "TeamDetailResult{" +
                "team=" + team.toString() +
                '}';
    }
}
