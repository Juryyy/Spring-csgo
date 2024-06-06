package com.example.csgo.domain.round;

import com.example.csgo.domain.match.Match;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "rounds")
public class Round {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "match_id", nullable = false)
    private Match match;

    @NotNull
    @Min(1)
    private int round;

    @NotNull
    @NotEmpty
    private String winnerSide;

    @NotNull
    @Min(0)
    private int ctEqVal;

    @NotNull
    @Min(0)
    private int tEqVal;

    public Round(Match match, int round, String winnerSide, int ctEqVal, int tEqVal) {
        this.match = match;
        this.round = round;
        this.winnerSide = winnerSide;
        this.ctEqVal = ctEqVal;
        this.tEqVal = tEqVal;
    }
}
