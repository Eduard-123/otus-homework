package atm;

import atm.model.Denomination;
import atm.model.exception.*;
import atm.model.Cassette;
import atm.model.operation.CashBoxOperation;
import atm.utils.AtmUtils;
import com.google.common.base.Strings;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static atm.utils.AtmUtils.throwExceptionIfTrue;

public class Atm implements CashBoxOperation {

    private static final String DELIMITER = ";";
    private List<Cassette> cassettes;

    private Atm(List<Cassette> cassettes) {
        updateCassettesWithSort(cassettes);
    }

    private void updateCassettesWithSort(List<Cassette> cassettes) {
        cassettes.sort(Cassette::compare);
        this.cassettes = cassettes;
    }

    @Override
    public long getCurrentAmount() {
        return cassettes.stream()
                .map(Cassette::getCurrentAmount)
                .reduce(Long::sum)
                .orElse(0L);
    }

    @Override
    public boolean isEmpty() {
        return cassettes.isEmpty() || cassettes.stream().allMatch(Cassette::isEmpty);
    }

    @Override
    public String backup() {
        return cassettes.stream()
                .map(Cassette::backup)
                .collect(Collectors.joining(DELIMITER));
    }

    @Override
    public void restore(String restoringState) {
        throwExceptionIfTrue(Strings.isNullOrEmpty(restoringState) || restoringState.isBlank(),
                CashBoxStateIsWrongException::new);

        this.cassettes = Stream.of(restoringState.split(DELIMITER))
                .map(Cassette::restore)
                .sorted(Cassette::compare)
                .collect(Collectors.toList());
    }

    public static Builder builder() {
        return new Builder();
    }

    public void addBanknotes(Map<Denomination, Long> banknotes) {
        if (banknotes != null && !banknotes.isEmpty()) {
            List<Cassette> _cassettes = new ArrayList<>();
            for (Cassette cassette : cassettes) {
                Long _banknotes = banknotes.getOrDefault(cassette.getDenomination(), 0L);
                throwExceptionIfTrue(_banknotes == null || _banknotes < 0, InvalidBanknotesCountException::new);
                _cassettes.add(cassette.addBanknotes(_banknotes));
                banknotes.remove(cassette.getDenomination());
            }
            throwExceptionIfTrue(banknotes.isEmpty(), UnknownDenominationException::new);
            updateCassettesWithSort(_cassettes);
        }
    }

    @Override
    public Map<Denomination, Long> removeBanknotes(long amount) {
        throwExceptionIfTrue(amount <= 0, InvalidAmountException::new);
        List<Cassette> newCashBoxState = new ArrayList<>();
        Map<Denomination, Long> removingBanknotes = new HashMap<>();
        if (amount <= getCurrentAmount()) {
            for (Cassette cassette : cassettes) {
                long banknotes = amount / cassette.getDenomination().getAmount();
                Cassette newState = cassette.removeBanknotes(banknotes);
                long removedBanknotes = cassette.getCount() - newState.getCount();
                newCashBoxState.add(newState);
                amount = amount - (removedBanknotes * cassette.getDenomination().getAmount());
                if (removedBanknotes > 0) {
                    removingBanknotes.put(cassette.getDenomination(), removedBanknotes);
                }
            }
        }
        throwExceptionIfTrue(amount != 0, NotEnoughBanknotesException::new);
        updateCassettesWithSort(newCashBoxState);
        return removingBanknotes;
    }

    public static class Builder {
        private final List<Cassette> cassettes = new ArrayList<>();

        private Builder() {
        }

        public Builder addCassettes(Cassette cassette) {
            throwExceptionIfTrue(AtmUtils.isNullOrEmpty(cassette), CassetteIsEmptyException::new);
            this.cassettes.add(cassette.cloneThis());
            return this;
        }

        public Atm build() {
            throwExceptionIfTrue(cassettes.isEmpty(), CashBoxIsEmptyException::new);
            return new Atm(cassettes);
        }
    }
}
