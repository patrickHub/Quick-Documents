/*! Copyright (c) 2021 Quick Document. All Rights Reserved. */


"use strict";

import './modules/qd-common.js';
import QdValidator from './modules/qd-validator.js'; './modules/qd-validator';

var qdValidator = new QdValidator();

!function(){qdValidator.doValidation();}();