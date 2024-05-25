package com.backend.demo.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.backend.demo.dao.ShipmentDao;
import com.backend.demo.entity.Receiver;
import com.backend.demo.entity.Shipment;
import com.backend.demo.model.ReceiverResponse;
import com.backend.demo.model.ShipmentResponse;
import com.backend.demo.service.ShipmentService;

@RestController
public class ShipmentController {
	
	@Autowired
	private ShipmentService shipmentService;
	
	@GetMapping(value = "/shipment")
	public ResponseEntity<?> findAll() {
		List<Shipment> shipments = null;
		try {
			shipments = shipmentService.findAll();
			if (shipments.size() != 0) {
				return new ResponseEntity<>(new ShipmentResponse("Shipments Retrived Successfully", shipments), HttpStatus.OK);
			} else {
				return new ResponseEntity<>(new ShipmentResponse("There is no Shipments in DB", shipments), HttpStatus.OK);
			}
		} catch (Exception e) {
			return new ResponseEntity<>(new ShipmentResponse("something went wrong", shipments), HttpStatus.NOT_FOUND);

		}
	}
	
	@GetMapping(value = "/shipment/{id}")
	public ResponseEntity<?> findById(@PathVariable int id) {
		Shipment shipment= null;
		List<Shipment> shipments =new ArrayList<Shipment>();
		try {
			shipment = shipmentService.findById(id);
			shipments.add(shipment);
			return new ResponseEntity<>(new ShipmentResponse("Shipment Retrived Successfully", shipments), HttpStatus.OK);

		} catch (Exception e) {
			return new ResponseEntity<>(new ShipmentResponse("There is no Shipment with this id", shipments),HttpStatus.NOT_FOUND);
		}
	}
	
	@PostMapping(value = "/shipment")
	public ResponseEntity<?> addItem(@RequestBody Shipment shipment){
		Shipment myShipment= null;
		List<Shipment> shipments =new ArrayList<Shipment>();

		try {
			myShipment=shipmentService.addShipment(shipment);
			shipments.add(myShipment);
			return new ResponseEntity<>(new ShipmentResponse("Shipment is added successfully", shipments), HttpStatus.OK);
		}catch(Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(new ShipmentResponse("Shipment can not added", shipments), HttpStatus.NOT_FOUND);

		}
	}
	
	@PutMapping(value = "/shipment")
	public ResponseEntity<?> updateItem(@RequestBody Shipment shipment){
		Shipment myShipment= null;
		int id = shipment.getId();
		List<Shipment> shipments =new ArrayList<Shipment>();
		try {
			myShipment=shipmentService.updateShipment(id, shipment);
			shipments.add(myShipment);
			return new ResponseEntity<>(new ShipmentResponse("Shipment is updated Successfully", shipments),HttpStatus.OK);

		} catch (Exception e) {
			return new ResponseEntity<>(new ShipmentResponse("Shipment Can Not update this id doesnt exist", shipments),HttpStatus.NOT_FOUND);

		}
	}
	
	@DeleteMapping(value="/shipment/{id}")
	public ResponseEntity<?> deleteCustomer(@PathVariable int id) {
		try {
			shipmentService.deleteShipment(id);
			return new ResponseEntity<>(new ShipmentResponse("Shipment is Deleted Successfully"),HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(new ShipmentResponse("Can not delete this Shipment,Shipment id is not exist"),HttpStatus.NOT_FOUND);
		}
	}

}