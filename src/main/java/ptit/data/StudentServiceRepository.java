package ptit.data;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import ptit.StudentService;

public interface StudentServiceRepository extends CrudRepository<StudentService, Integer> {
    @Query(value = "SELECT * FROM tblStudentService WHERE studentid = ?1 AND serviceid = ?2", nativeQuery = true)
    StudentService findByStudentidAndServiceid(Long studentid, Long serviceid);

    @Query(value = "SELECT * FROM tblStudentService WHERE serviceid = ?1 ORDER BY `date` DESC", nativeQuery = true)
    ArrayList<StudentService> findByService_Id(Long id);

    @Query(value = "SELECT * FROM tblStudentService WHERE serviceid = ?1 AND MONTH(`date`) = ?2", nativeQuery = true)
    ArrayList<StudentService> stat(Long id, int month);

    @Query(value = "SELECT * FROM tblStudentService WHERE serviceid = ?1 AND studentid = ?2", nativeQuery = true)
    ArrayList<StudentService> stuStat(Long id, int stuid);

    @Query(value = "SELECT * FROM tblStudentService WHERE studentid = ?1 AND MONTH(`date`) = ?2", nativeQuery = true)
    ArrayList<StudentService> bill(int i, int month);
}
