package io.javabrains.ipl_dashboard_re.data;

import java.time.LocalDate;
import java.time.LocalDateTime;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.batch.item.ItemProcessor;

import io.model.Match;

public class MatchDataProcessor implements ItemProcessor<MatchInput, Match> {

    private static final Logger log = LoggerFactory.getLogger(MatchDataProcessor.class);

    @Override
    public Match process(final MatchInput matchInput) throws Exception {
        
        Match match = new Match();
        match.setId(Long.parseLong(matchInput.getId()));
        match.setCity(matchInput.getCity());

        match.setDate(LocalDate.parse(matchInput.getDate()));
        match.setPlayerOfMatch(matchInput.getPlayer_of_match());
        match.setVenue(matchInput.getVenue());

        // Set team1 and team2 depending on the innings order
        String firstInningsTeam, seondInningsTeam;
        if("bat".equals(matchInput.getToss_decision())){
            firstInningsTeam = matchInput.getToss_winner();
            seondInningsTeam = firstInningsTeam.equals(matchInput.getTeam1()) ? matchInput.getTeam2() : matchInput.getTeam1();
        } else {
            seondInningsTeam = matchInput.getToss_winner();
            firstInningsTeam = seondInningsTeam.equals(matchInput.getTeam1()) ? matchInput.getTeam2() : matchInput.getTeam1();
        }

        match.setTeam1(firstInningsTeam);
        match.setTeam2(seondInningsTeam);

        match.setTossWinner(matchInput.getToss_winner());
        match.setTossDecision(matchInput.getToss_decision());
        match.setResult(matchInput.getResult());
        match.setResultMargin(matchInput.getResult_margin());
        match.setUmpire1(matchInput.getUmpire1());
        match.setUmpire2(matchInput.getUmpire2());
        
        return match; 
    }

}
