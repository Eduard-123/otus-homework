package atm.model.operation;

import atm.model.state.BackupState;
import atm.model.state.RestoreState;

public interface CashBoxOperation extends CurrentAmount, AddBanknotes, RemoveBanknotes, Empty, BackupState, RestoreState {
}
