package com.shabbir.Jwt_Impl.Service;

import com.shabbir.Jwt_Impl.Entity.JournalEntry;
import com.shabbir.Jwt_Impl.Entity.User;
import com.shabbir.Jwt_Impl.Repository.JournalRepository;
import com.shabbir.Jwt_Impl.Repository.UserRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class JournalService {

    @Autowired
    JournalRepository journalRepository;
    @Autowired
    UserRepository userRepository;

    public ResponseEntity<?> addNewEntry(JournalEntry newEntry, String username){
        User userInDb = userRepository.findByUsername(username);
        if(userInDb != null){
            JournalEntry saved = journalRepository.save(newEntry);
            userInDb.getJournalEntries().add(saved);
            userRepository.save(userInDb);
            return new ResponseEntity<>(HttpStatus.CREATED);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    public ResponseEntity<List<JournalEntry>> getAllEntry(String username){
        User userInDb = userRepository.findByUsername(username);
        if(userInDb != null){
            return new ResponseEntity<>(userInDb.getJournalEntries(),HttpStatus.OK);
        }
        return new ResponseEntity<>(null,HttpStatus.BAD_REQUEST);
    }

    public ResponseEntity<Boolean> updateSingleEntry(JournalEntry updateEntry, String username, ObjectId id){
        User userInDb = userRepository.findByUsername(username);
        if(userInDb != null){
            List<JournalEntry> collect = userInDb.getJournalEntries().stream().filter(x -> x.getId().equals(id)).collect(Collectors.toList());
            if(!collect.isEmpty()){
                Optional<JournalEntry> findById = journalRepository.findById(id);
                JournalEntry oldEntry = findById.get();
                oldEntry.setTitle(updateEntry.getTitle() != null && updateEntry.getTitle() != "" ? updateEntry.getTitle() : oldEntry.getTitle());
                oldEntry.setContent(updateEntry.getContent() != null && updateEntry.getContent() != ""? updateEntry.getContent(): oldEntry.getContent());
                journalRepository.save(oldEntry);
                return new ResponseEntity<>(true,HttpStatus.CREATED);
            }
        }
        return new ResponseEntity<>(false,HttpStatus.BAD_REQUEST);
    }

    public ResponseEntity<Boolean> deleteSingleEntry(String username, ObjectId id){
        User userInDb = userRepository.findByUsername(username);
        if(userInDb != null){
            boolean isRemoveFromTheList = userInDb.getJournalEntries().removeIf(x -> x.getId().equals(id));
            if(isRemoveFromTheList){
                journalRepository.deleteById(id);
                return new ResponseEntity<>(true,HttpStatus.OK);
            }
        }
        return new ResponseEntity<>(false,HttpStatus.BAD_REQUEST);
    }

}
