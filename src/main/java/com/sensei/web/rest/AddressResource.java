package com.sensei.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.sensei.domain.Address;
import com.sensei.domain.User;
import com.sensei.repository.AddressRepository;
import com.sensei.service.UserService;
import com.sensei.web.rest.util.HeaderUtil;
import com.sensei.web.rest.util.ResponseUtil;
import com.sensei.web.rest.vm.AddressVm;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing Address.
 */
@RestController
@RequestMapping("/api")
public class AddressResource {

    private final Logger log = LoggerFactory.getLogger(AddressResource.class);

    private static final String ENTITY_NAME = "address";

    private final AddressRepository addressRepository;
    
    private final UserService userService;

    public AddressResource(AddressRepository addressRepository, UserService userService) {
        this.addressRepository = addressRepository;
        this.userService = userService;
    }

    /**
     * POST  /addresses : Create a new address.
     *
     * @param address the address to create
     * @return the ResponseEntity with status 201 (Created) and with body the new address, or with status 400 (Bad Request) if the address has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/addresses")
    @Timed
    public ResponseEntity<Address> createAddress(@Valid @RequestBody AddressVm addressVM) throws URISyntaxException {
        log.debug("REST request to save Address : {}", addressVM);
       /* if (address.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new address cannot already have an ID")).body(null);
        }*/
        
        User user = getUser(addressVM);
        if(user == null)
        {
        	return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "User does not exist")).body(null);
        }
        Address address = populateRequest(addressVM);
        address.setUser(user);
        Address result = addressRepository.save(address);
        return ResponseEntity.created(new URI("/api/addresses/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    private Address populateRequest(AddressVm addressVM) 
    {
    	
    	Address address = new Address();
    	
    	address.setAddressLine1(addressVM.getAddressLine1());
    	address.setAddressline2(addressVM.getAddressLine2());
    	address.setCity(addressVM.getCity());
    	address.setState(addressVM.getState());
    	address.setZipcode(addressVM.getZipcode());
    	address.setCountry(addressVM.getCountry());
    	
		return address;
	}
    
	private User getUser(AddressVm addressVM) 
	{
		User senseiUser = null;
        Optional<User> user = userService.getUserByEmail(addressVM.getUsername());
           
        if(user.isPresent())
        {
        	senseiUser = user.get();
        }
		return senseiUser;
	}

	/**
     * PUT  /addresses : Updates an existing address.
     *
     * @param address the address to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated address,
     * or with status 400 (Bad Request) if the address is not valid,
     * or with status 500 (Internal Server Error) if the address couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/addresses")
    @Timed
    public ResponseEntity<Address> updateAddress(@Valid @RequestBody AddressVm addressVM) throws URISyntaxException {
        log.debug("REST request to update Address : {}", addressVM);
        if (addressVM.getId() == null) {
            return createAddress(addressVM);
        }
        
        Address address = populateRequest(addressVM);
        Address result = addressRepository.save(address);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, address.getId().toString()))
            .body(result);
    }

    /**
     * GET  /addresses : get all the addresses.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of addresses in body
     */
    @GetMapping("/addresses")
    @Timed
    public List<Address> getAllAddresses() {
        log.debug("REST request to get all Addresses");
        return addressRepository.findAll();
    }

    /**
     * GET  /addresses/:id : get the "id" address.
     *
     * @param id the id of the address to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the address, or with status 404 (Not Found)
     */
    @GetMapping("/addresses/{id}")
    @Timed
    public ResponseEntity<Address> getAddress(@PathVariable Long id) {
        log.debug("REST request to get Address : {}", id);
        Address address = addressRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(address));
    }

    /**
     * DELETE  /addresses/:id : delete the "id" address.
     *
     * @param id the id of the address to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/addresses/{id}")
    @Timed
    public ResponseEntity<Void> deleteAddress(@PathVariable Long id) {
        log.debug("REST request to delete Address : {}", id);
        addressRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}