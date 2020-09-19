package atm.model.operation;

import atm.model.state.BackupState;
import atm.model.state.RestoreState;

public interface DepartmentOperation extends CurrentAmount, BackupState, RestoreState {
}
