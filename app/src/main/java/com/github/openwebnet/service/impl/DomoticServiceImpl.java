package com.github.openwebnet.service.impl;

import android.app.Application;

import com.github.openwebnet.OpenWebNetApplication;
import com.github.openwebnet.R;
import com.github.openwebnet.model.DeviceModel;
import com.github.openwebnet.model.EnvironmentModel;
import com.github.openwebnet.model.GatewayModel;
import com.github.openwebnet.model.LightModel;
import com.github.openwebnet.repository.DeviceRepository;
import com.github.openwebnet.repository.EnvironmentRepository;
import com.github.openwebnet.repository.GatewayRepository;
import com.github.openwebnet.repository.LightRepository;
import com.github.openwebnet.service.DomoticService;
import com.github.openwebnet.service.PreferenceService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

import javax.inject.Inject;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * @author niqdev
 */
public class DomoticServiceImpl implements DomoticService {

    private static final Logger log = LoggerFactory.getLogger(DomoticService.class);

    private final Application application;

    @Inject
    public DomoticServiceImpl(Application application) {
        OpenWebNetApplication.component(application).inject(this);
        this.application = application;
    }

    @Inject PreferenceService preferenceService;

    @Inject EnvironmentRepository environmentRepository;

    @Inject GatewayRepository gatewayRepository;

    @Inject LightRepository lightRepository;

    @Inject DeviceRepository deviceRepository;

    @Override
    public void initRepository() {
        if (preferenceService.isFirstRun()) {
            addEnvironment(getString(R.string.drawer_menu_example))
                .subscribe(id -> {
                    log.debug("initRepository with success");
                }, throwable -> {
                    log.error("initRepository", throwable);
                });
            preferenceService.initFirstRun();
        }
    }

    @Override
    public Observable<Integer> addEnvironment(String name) {
        return environmentRepository.getNextId()
            .map(id -> {
                EnvironmentModel environment = new EnvironmentModel();
                environment.setId(id);
                environment.setName(name);
                return environment;
            })
            .flatMap(environment -> environmentRepository.add(environment))
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public Observable<List<EnvironmentModel>> findAllEnvironment() {
        return environmentRepository.findAll();
    }

    @Override
    public Observable<String> addGateway(String host, Integer port) {
        return gatewayRepository.add(GatewayModel.newGateway(host, port))
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public Observable<List<GatewayModel>> findAllGateway() {
        return gatewayRepository.findAll();
    }

    @Override
    public Observable<String> addLight(LightModel.Builder light) {
        return lightRepository.add(light.build())
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public Observable<List<LightModel>> findAllLight() {
        return lightRepository.findAll();
    }

    @Override
    public Observable<String> addDevice(DeviceModel.Builder device) {
        return deviceRepository.add(device.build())
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public Observable<List<DeviceModel>> findAllDevice() {
        return deviceRepository.findAll();
    }

    private String getString(int id) {
        return application.getResources().getString(id);
    }
}
