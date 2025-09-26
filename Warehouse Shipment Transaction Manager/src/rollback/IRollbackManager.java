package rollback;

/**
 * Interface for rollback operations (undo last state).
 */
public interface IRollbackManager {
    void rollbackStorage();
    void rollbackCourier();
}

