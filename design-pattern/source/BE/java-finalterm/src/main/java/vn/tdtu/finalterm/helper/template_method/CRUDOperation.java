package vn.tdtu.finalterm.helper.template_method;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import vn.tdtu.finalterm.models.ResponseObject;

import java.util.List;
import java.util.Optional;

public abstract class CRUDOperation<T> {
    public ResponseEntity<ResponseObject> findAll() {
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject("ok", "Query All " + getTypeName()
                        + " Success", getAll())
        );
    }

    public ResponseEntity<ResponseObject> findById(Long id) {
        Optional<T> entity = getRepository().findById(id);

        if (!entity.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    new ResponseObject("failed", "Can't find " + getTypeName()
                            + " with id = " + id, "")
            );
        }

        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject("ok", "Query " + getTypeName()
                        + " Success", entity.get())
        );
    }

    public ResponseEntity<ResponseObject> update(T entity, Long id) {
        Optional<T> foundEntity = getRepository().findById(id);

        if (!foundEntity.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    new ResponseObject("failed", "Can't find " + getTypeName()
                            + " with id = " + id, "")
            );
        }

        T existingEntity = foundEntity.get();
        T updatedEntity = updateEntity(existingEntity, entity);

        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject("ok", "Update " + getTypeName()
                        + " Success", updatedEntity)
        );
    }

    public ResponseEntity<ResponseObject> delete(Long id) {
        Optional<T> foundEntity = getRepository().findById(id);

        if (!foundEntity.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    new ResponseObject("failed", "Can't find " + getTypeName()
                            + " with id = " + id, "")
            );
        }

        T entityToDelete = foundEntity.get();
        deleteEntity(entityToDelete);

        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject("ok", "Delete " + getTypeName()
                        + " Success", "")
        );
    }

    protected abstract String getTypeName();

    protected abstract List<T> getAll();

    protected abstract T updateEntity(T existingEntity, T newEntity);

    protected abstract void deleteEntity(T entity);

    protected abstract JpaRepository<T, Long> getRepository();
}
